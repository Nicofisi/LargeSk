package pl.pickaxe.largesk.expressions;

import org.bukkit.event.Event;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Nullable;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprLinkGoogle extends SimpleExpression<String>
{
	private Expression<String> search;
	
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
		search = (Expression<String>) expr[0];
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
		try
		{
			return new String[] {"https://www.bing.com/search?q=" + URLEncoder.encode(search.getSingle(e).toString(),"UTF-8")};
		}
		catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
			return null;
		}
	}
	
}
