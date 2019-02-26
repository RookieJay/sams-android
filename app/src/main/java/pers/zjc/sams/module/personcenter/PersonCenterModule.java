package pers.zjc.sams.module.personcenter;

import dagger.Module;
import dagger.Provides;
import pers.zjc.sams.module.personcenter.contract.PersonCenterContract;

@Module
public class PersonCenterModule {

    private PersonCenterContract.View view;

    public PersonCenterModule(PersonCenterContract.View view) {
        this.view = view;
    }

    @Provides
    PersonCenterContract.View provideView() {
        return this.view;
    }
}
