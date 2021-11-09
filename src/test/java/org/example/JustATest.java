package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JustATest {
    @Test
    void ofGitHubActions() {
        assertEquals(4, addUp(2, 2));
    }

    private int addUp(int a, int b) {
        return a + b;
    }
}
