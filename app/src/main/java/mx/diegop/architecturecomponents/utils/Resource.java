package mx.diegop.architecturecomponents.utils;

import android.support.annotation.Nullable;

import javax.annotation.Nonnull;

import static mx.diegop.architecturecomponents.utils.Status.ERROR;
import static mx.diegop.architecturecomponents.utils.Status.LOADING;
import static mx.diegop.architecturecomponents.utils.Status.SUCCESS;

public class Resource<T> {

    @Nonnull
    public final Status status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;

    public Resource(@Nonnull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }

    public static <T> Resource<T> error(String message, @Nullable T data) {
        return new Resource<>(ERROR, data, message);
    }
}
