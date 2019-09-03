package ru.vpcb.map.notes.fragments.add;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.vpcb.map.notes.FragmentTestActivity;
import ru.vpcb.map.notes.MockTest;
import ru.vpcb.map.notes.R;

import static ru.vpcb.map.notes.robots.HomeScreenRobot.addNoteFragment;
import static ru.vpcb.map.notes.robots.PreparationRobot.prepare;
import static ru.vpcb.map.notes.robots.TestActivityRobot.testFragmentActivity;
import static ru.vpcb.map.notes.robots.TestActivityRobot.testScreen;

@RunWith(AndroidJUnit4.class)
public class AddNoteFragmentTest extends MockTest {


    @Rule
    public ActivityTestRule<FragmentTestActivity> activityRule = testFragmentActivity; // auto run

    private String testNoteText;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        testNoteText = "test note";

        app.put(FragmentTestActivity.class, homeBuilder);

        prepare(testScope)
                .mockLocationProvider(true)
                .mockAuthorizedUser();
        testScreen()
                .attachFragment(new AddNoteFragment());
    }

    @Test
    public void shouldDisplayNoteHintForANewNote() {
        addNoteFragment()
                .isNoteHintDisplayed(R.string.add_note_hint);
    }

    @Test
    public void shouldChangeAddButtonEnableAfterChangingNoteText() {
        addNoteFragment()
                .isAddButtonDisabled()
                .enterNoteText(testNoteText)
                .isAddButtonEnabled();
    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
