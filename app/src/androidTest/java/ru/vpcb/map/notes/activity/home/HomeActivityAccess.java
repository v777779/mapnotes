package ru.vpcb.map.notes.activity.home;

import ru.vpcb.map.notes.fragments.map.MapFragment;
import ru.vpcb.map.notes.manager.FAManager;

public class HomeActivityAccess {
    public static void set(HomeActivity activity, HomeMvpPresenter presenter,
                           MapFragment mapFragment, FAManager analyticManager) {
        activity.presenter = presenter;
        activity.mapFragment = mapFragment;
        activity.analyticsManager = analyticManager;
    }
}
