package com.everalbum.roliedex;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class RoliedexLayoutTest {

    private RoliedexLayout layout;

    @Before
    public void setup() {
        layout = new RoliedexLayout(mock(Context.class), null, 0, 0, true);
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
}