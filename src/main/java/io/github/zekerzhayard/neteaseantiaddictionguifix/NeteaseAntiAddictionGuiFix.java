package io.github.zekerzhayard.neteaseantiaddictionguifix;

import java.lang.reflect.Proxy;
import java.util.Map;

import org.apache.commons.lang3.reflect.FieldUtils;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = "neteaseantiaddictionguifix", name = "NeteaseAntiAddictionGuiFix", version = "@version@", dependencies = "after:antimod;", acceptedMinecraftVersions = "[1.8,1.12.2]")
public class NeteaseAntiAddictionGuiFix {
	@Mod.EventHandler()
	@SuppressWarnings(value = { "unchecked" })
	public void init(FMLInitializationEvent event) throws IllegalAccessException {
		((Map<Object, ModContainer>) FieldUtils.readDeclaredField(MinecraftForge.EVENT_BUS, "listenerOwners", true)).keySet().stream().filter(o -> o.getClass().getName().equals("com.netease.mc.mod.antiAddiction.AntiAddictionEventHandlerClient")).forEachOrdered(o -> {
			MinecraftForge.EVENT_BUS.unregister(o);
			try {
				((Map<String, Object>) FieldUtils.readDeclaredField(Proxy.getInvocationHandler(o.getClass().getMethod("onRenderTickEvent", TickEvent.RenderTickEvent.class).getAnnotation(SubscribeEvent.class)), "memberValues", true)).put("priority", EventPriority.LOWEST);
			} catch (IllegalAccessException | NoSuchMethodException e) {
				e.printStackTrace();
			}
			MinecraftForge.EVENT_BUS.register(o);
		});
	}
}
