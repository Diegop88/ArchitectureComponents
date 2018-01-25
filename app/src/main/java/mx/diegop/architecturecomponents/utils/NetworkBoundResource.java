package mx.diegop.architecturecomponents.utils;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;

import java.util.Objects;

public abstract class NetworkBoundResource<ResultType, RequestType> {
    private static final String TAG = NetworkBoundResource.class.getSimpleName();

    private final AppExecutors executors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    protected NetworkBoundResource(AppExecutors executors) {
        this.executors = executors;
        result.setValue(Resource.<ResultType>loading(null));
        final LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType resultType) {
                result.removeSource(dbSource);
                if (shouldFetch(resultType)) {
                    fetchFromNetwork(dbSource);
                } else {
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(@Nullable ResultType resultType) {
                            result.setValue(Resource.success(resultType));
                        }
                    });
                }
            }
        });
    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        final MutableLiveData<Resource<RequestType>> apiResponse = createCall();
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType newData) {
                result.setValue(Resource.loading(newData));
            }
        });
        result.addSource(apiResponse, new Observer<Resource<RequestType>>() {
            @Override
            public void onChanged(final Resource<RequestType> response) {
                result.removeSource(apiResponse);
                result.removeSource(dbSource);
                if (Status.SUCCESS.equals(response.status)) {
                    executors.getDiskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            saveCallResult(processResponse(response));
                            executors.getMainThread().execute(new Runnable() {
                                @Override
                                public void run() {
                                    result.addSource(loadFromDb(), new Observer<ResultType>() {
                                        @Override
                                        public void onChanged(@Nullable ResultType resultType) {
                                            setValue(Resource.success(resultType));
                                        }
                                    });
                                }
                            });
                        }
                    });
                } else {
                    onFetchFailed();
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(@Nullable ResultType resultType) {
                            setValue(Resource.error(response.message, resultType));
                        }
                    });
                }
            }
        });
    }

    protected void onFetchFailed() {
        Log.d(TAG, "Fetch failed");
    }

    public final LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    private RequestType processResponse(Resource<RequestType> response) {
        return response.data;
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract MutableLiveData<Resource<RequestType>> createCall();
}
