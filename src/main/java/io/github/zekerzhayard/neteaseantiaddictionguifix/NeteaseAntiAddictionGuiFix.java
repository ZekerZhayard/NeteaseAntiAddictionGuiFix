package io.github.zekerzhayard.neteaseantiaddictionguifix;

import java.util.Map;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.netease.mc.mod.antiAddiction.AntiAddictionEventHandlerClient;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = "neteaseantiaddictionguifix", name = "NeteaseAntiAddictionGuiFix", version = "@version@", clientSideOnly = true, dependencies = "after:antimod;", acceptedMinecraftVersions = "[1.8,1.12.2]", updateJSON = "https://raw.githubusercontent.com/ZekerZhayard/NeteaseAntiAddictionGuiFix/master/update.json")
public class NeteaseAntiAddictionGuiFix {
	private Object aaehc;
	
	@Mod.EventHandler()
	@Optional.Method(modid = "antimod")
	@SuppressWarnings(value = { "unchecked" })
	public void init(FMLInitializationEvent event) throws IllegalAccessException {
		for (Object o : ((Map<Object, ModContainer>) FieldUtils.readDeclaredField(MinecraftForge.EVENT_BUS, "listenerOwners", true)).keySet()) {
			if (o instanceof AntiAddictionEventHandlerClient) {
				this.aaehc = o;
				MinecraftForge.EVENT_BUS.unregister(o);
				MinecraftForge.EVENT_BUS.register(this);
				break;
			}
		}
	}
	
	@SubscribeEvent()
	public void onClientChatReceivedEvent(ClientChatReceivedEvent event) {
		((AntiAddictionEventHandlerClient) this.aaehc).ChatMessageReceiveEvent(event);
	}
	
	@SubscribeEvent()
	public void onClientTickEvent(TickEvent.ClientTickEvent event) {
		((AntiAddictionEventHandlerClient) this.aaehc).onClientTickEvent(event);
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onRenderTickEvent(TickEvent.RenderTickEvent event) {
		((AntiAddictionEventHandlerClient) this.aaehc).onRenderTickEvent(event);
	}
}
