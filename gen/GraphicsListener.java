// Generated from /Users/weinanli/projs/java-snippets/src/main/java/antlr/graphic/Graphics.g4 by ANTLR 4.5
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GraphicsParser}.
 */
public interface GraphicsListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GraphicsParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(@NotNull GraphicsParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphicsParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(@NotNull GraphicsParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphicsParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommand(@NotNull GraphicsParser.CommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphicsParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommand(@NotNull GraphicsParser.CommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraphicsParser#point}.
	 * @param ctx the parse tree
	 */
	void enterPoint(@NotNull GraphicsParser.PointContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraphicsParser#point}.
	 * @param ctx the parse tree
	 */
	void exitPoint(@NotNull GraphicsParser.PointContext ctx);
}