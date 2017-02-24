package main;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.usfirst.*;

import org.usfirst.frc.team2415.robot.StreamerPacket;

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