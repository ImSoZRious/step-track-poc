import type { PluginListenerHandle } from '@capacitor/core';

export interface PedometerPlugin {
  ping(options: { value: string }): Promise<{ value: string }>;

  /**
   * Create and bind service.
   */
  register(): Promise<void>;

  /**
   * Unbind and kill service.
   */
  unregister(): Promise<void>;

  addListener(
    eventName: 'step',
    listenerFunc: (event: SensorEvent) => void,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;

  addListener(
    eventName: 'activate',
    listenerFunc: (event: SensorEvent) => void,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;

  addListener(
    eventName: 'deactivate',
    listenerFunc: () => void,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;
}

export interface SensorEvent {
  /**
   * Time in Unix Epoch format
   * @since 1.0.0
   */
  startTime: number;

  /**
   * Time in Unix Epoch format
   * @since 1.0.0
   */
  endTime: number;

  /**
   * number of step which is count in the given time interval.
   * @since 1.0.0
   */
  steps: number;
}
