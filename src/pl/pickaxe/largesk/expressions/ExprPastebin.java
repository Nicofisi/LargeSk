package pl.pickaxe.largesk.expressions;

import org.bukkit.event.Event;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import pl.pickaxe.largesk.util.Pastebin;

public class ExprPastebin extends SimpleExpression<String>
{
	private Expression<String> textToPaste;
	private Expression<String> nameOfPaste;
	private Expression<String> expireDate;
	private Expression<String> pasteFormat;
	
	@Override
	public Class<? extends String> getReturnType()
	{
		return String.class;
	}

	@Override
	public boolean isSingle()
	{
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3)
	{
		textToPaste = (Expression<String>) expr[0];
		if ( ! ((Expression<String>) expr[1] == null))
		{
			nameOfPaste = (Expression<String>) expr[1];
		}
		if ( ! ((Expression<String>) expr[2] == null))
		{
			expireDate = (Expression<String>) expr[2];
		}
		if ( ! ((Expression<String>) expr[3] == null))
		{
			pasteFormat = (Expression<String>) expr[3];
		}
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1)
	{
		return null;
	}

	@Override
	@Nullable
	protected String[] get(Event e)
	{
		String test = "failed to connect";
		try
		{
			test = Pastebin.sendPost(textToPaste.getSingle(e), nameOfPaste.getSingle(e), expireDate.getSingle(e), pasteFormat.getSingle(e));
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
		return new String[] {test};
	}

}
