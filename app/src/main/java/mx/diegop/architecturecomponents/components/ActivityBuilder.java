package mx.diegop.architecturecomponents.components;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import mx.diegop.architecturecomponents.MainActivity;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();
}
