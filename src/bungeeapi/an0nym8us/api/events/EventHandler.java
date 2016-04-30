package an0nym8us.api.events;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/** This annotation is similar to Bukkit's EventHandler annotation, but is used by MessagingAPI event system
 * @author An0nym8us
 *
 */
@Target({ ElementType.METHOD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface EventHandler {	}
