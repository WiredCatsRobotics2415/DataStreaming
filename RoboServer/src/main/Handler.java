package main;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public abstract class Handler implements Runnable{

	abstract void setPacket(StreamerPacket packet);
	abstract StreamerPacket getPacket();
	public Handler(){
		
	}
	
	
	
	
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface name{
	String type() default "";
}