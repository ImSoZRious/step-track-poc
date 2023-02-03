# pedometer-plugin

foreground service for pedometer.

## Install

```bash
npm install pedometer-plugin
npx cap sync
```

## API

<docgen-index>

* [`ping(...)`](#ping)
* [`register()`](#register)
* [`unregister()`](#unregister)
* [`addListener('step', ...)`](#addlistenerstep)
* [`addListener('activate', ...)`](#addlisteneractivate)
* [`addListener('deactivate', ...)`](#addlistenerdeactivate)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### ping(...)

```typescript
ping(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### register()

```typescript
register() => Promise<void>
```

Create and bind service.

--------------------


### unregister()

```typescript
unregister() => Promise<void>
```

Unbind and kill service.

--------------------


### addListener('step', ...)

```typescript
addListener(eventName: 'step', listenerFunc: (event: SensorEvent) => void) => Promise<PluginListenerHandle> & PluginListenerHandle
```

| Param              | Type                                                                    |
| ------------------ | ----------------------------------------------------------------------- |
| **`eventName`**    | <code>'step'</code>                                                     |
| **`listenerFunc`** | <code>(event: <a href="#sensorevent">SensorEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


### addListener('activate', ...)

```typescript
addListener(eventName: 'activate', listenerFunc: (event: SensorEvent) => void) => Promise<PluginListenerHandle> & PluginListenerHandle
```

| Param              | Type                                                                    |
| ------------------ | ----------------------------------------------------------------------- |
| **`eventName`**    | <code>'activate'</code>                                                 |
| **`listenerFunc`** | <code>(event: <a href="#sensorevent">SensorEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


### addListener('deactivate', ...)

```typescript
addListener(eventName: 'deactivate', listenerFunc: () => void) => Promise<PluginListenerHandle> & PluginListenerHandle
```

| Param              | Type                       |
| ------------------ | -------------------------- |
| **`eventName`**    | <code>'deactivate'</code>  |
| **`listenerFunc`** | <code>() =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


### Interfaces


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### SensorEvent

| Prop            | Type                | Description                                               | Since |
| --------------- | ------------------- | --------------------------------------------------------- | ----- |
| **`startTime`** | <code>number</code> | Time in Unix Epoch format                                 | 1.0.0 |
| **`endTime`**   | <code>number</code> | Time in Unix Epoch format                                 | 1.0.0 |
| **`steps`**     | <code>number</code> | number of step which is count in the given time interval. | 1.0.0 |

</docgen-api>
