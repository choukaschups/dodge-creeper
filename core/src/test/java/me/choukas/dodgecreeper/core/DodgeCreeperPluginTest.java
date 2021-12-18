package me.choukas.dodgecreeper.core;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.junit.After;
import org.junit.Before;

public class DodgeCreeperPluginTest {

    private ServerMock server;
    private DodgeCreeperPlugin plugin;

    @Before
    public void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(DodgeCreeperPlugin.class);
    }

    @After
    public void tearDown() {
        MockBukkit.unload();
    }
}
