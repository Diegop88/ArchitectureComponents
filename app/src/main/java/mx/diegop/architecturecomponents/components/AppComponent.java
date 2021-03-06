package mx.diegop.architecturecomponents.components;

import android.app.Application;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import mx.diegop.architecturecomponents.IssuesApplication;
import mx.diegop.architecturecomponents.modules.AppModule;


@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class
})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(IssuesApplication app);
}
