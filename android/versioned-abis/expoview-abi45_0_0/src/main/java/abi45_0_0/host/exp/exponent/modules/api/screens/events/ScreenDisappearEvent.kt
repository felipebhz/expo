package abi45_0_0.host.exp.exponent.modules.api.screens.events

import abi45_0_0.com.facebook.react.bridge.Arguments
import abi45_0_0.com.facebook.react.uimanager.events.Event
import abi45_0_0.com.facebook.react.uimanager.events.RCTEventEmitter

class ScreenDisappearEvent(viewId: Int) : Event<ScreenDisappearEvent>(viewId) {
  override fun getEventName() = EVENT_NAME

  // All events for a given view can be coalesced.
  override fun getCoalescingKey(): Short = 0

  override fun dispatch(rctEventEmitter: RCTEventEmitter) {
    rctEventEmitter.receiveEvent(viewTag, eventName, Arguments.createMap())
  }

  companion object {
    const val EVENT_NAME = "topDisappear"
  }
}
