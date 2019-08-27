package ru.vpcb.map.notes.robots;

import androidx.fragment.app.Fragment;
import androidx.test.rule.ActivityTestRule;

import ru.vpcb.map.notes.FragmentTestActivity;

public class TestActivityRobot extends BaseTestRobot {
    public static ActivityTestRule<FragmentTestActivity> testFragmentActivity =
            new ActivityTestRule<>(FragmentTestActivity.class);

    public static TestActivityRobot testScreen() {
        return new TestActivityRobot();
    }


    public void attachFragment(Fragment fragment) {
        testFragmentActivity.getActivity().setFragment(fragment);
    }

}
