//package pl.pickaxe.largesk.GeneralExpressions;
//
//import org.bukkit.Location;
//import org.bukkit.Material;
//import org.bukkit.block.Block;
//import org.bukkit.event.Event;
//import org.bukkit.material.Cake;
//import org.bukkit.material.MaterialData;
//
//import javax.annotation.Nullable;
//
//import ch.njol.skript.classes.Changer;
//import ch.njol.skript.classes.Changer.ChangeMode;
//import ch.njol.skript.lang.Expression;
//import ch.njol.skript.lang.SkriptParser.ParseResult;
//import ch.njol.skript.lang.util.SimpleExpression;
//import ch.njol.util.Kleenean;
//import ch.njol.util.coll.CollectionUtils;
//
//public class ExprCakeSlicesEaten extends SimpleExpression<Integer> {
//	private Expression<Location> loc;
//	private Cake ck;
//	@Override
//	public Class<? extends Integer> getReturnType() {
//		return null;
//	}
//
//	@Override
//	public boolean isSingle() {
//		return true;
//	}
//	@SuppressWarnings("unchecked")
//	@Override
//	public boolean init(Expression<?>[] expr, int arg1, Kleenean arg2, ParseResult arg3) {
//		loc = (Expression<Location>) expr[0];
//		ck.
//		return true;
//	}
//
//	@Override
//	public String toString(@Nullable Event arg0, boolean arg1) {
//		return "amount of slices eaten";
//	}
//
//	@Override
//	@Nullable
//	protected Integer[] get(Event e) {
//		if (loc.getSingle(e).getBlock().getType() == Material.CAKE_BLOCK)
//		return new Integer[] {loc.getSingle(e).getBlock().get };
//	}
//		
//	@Override
//	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
//		if (mode == ChangeMode.SET)
//			cake.getSingle(e).setSlicesEaten((Integer)delta[0]);
//		if (mode == ChangeMode.ADD)
//			cake.getSingle(e).setSlicesEaten(cake.getSingle(e).getSlicesEaten() + (Integer)delta[0]);
//		if (mode == ChangeMode.REMOVE)
//			cake.getSingle(e).setSlicesEaten(cake.getSingle(e).getSlicesEaten() - (Integer)delta[0]);
//		if (mode == ChangeMode.RESET || mode == ChangeMode.DELETE)
//			loc.getSingle(e).getBlock();
//	}
//	
//	@SuppressWarnings("unchecked")
//	@Override
//	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
//		if (mode == ChangeMode.SET || mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.RESET || mode == ChangeMode.DELETE)
//			return CollectionUtils.array(Integer.class);
//		return null;
//	}
//	
//		
//		
//
//}
