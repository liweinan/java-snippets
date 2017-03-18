package net.bluedash.snippets.rpn;

import my.rpn.LexerException;
import my.rpn.Rpn;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class RpnTest {

    @Test
    public void testRpn() throws LexerException {
        Rpn rpn = new Rpn();
        rpn.Input = "1 + 2 + 3";

        assertEquals(rpn.TOK_NUM, rpn.getNextToken());
        assertEquals(rpn.TOK_OP, rpn.getNextToken());
        assertEquals(rpn.TOK_NUM, rpn.getNextToken());
        assertEquals(rpn.TOK_OP, rpn.getNextToken());
        assertEquals(rpn.TOK_NUM, rpn.getNextToken());

        assertEquals("1", rpn.Args.remove());
        assertEquals("+", rpn.Args.remove());
        assertEquals("2", rpn.Args.remove());
        assertEquals("+", rpn.Args.remove());
        assertEquals("3", rpn.Args.remove());

    }
}
