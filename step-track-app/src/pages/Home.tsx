import { useState } from 'react';
import {
  IonContent,
  IonHeader,
  IonPage,
  IonTitle,
  IonToolbar,
  IonGrid,
  IonRow,
  IonCol,
  IonButton,
} from '@ionic/react';
import './Home.css';

import { Pedometer, SensorEvent } from 'pedometer-plugin';

const enum AppState {
  Unregister = 0,
  Registering = 1,
  Registered = 2,
}

const Home: React.FC = () => {
  const [stepCount, setStepCount] = useState(0);

  const [registered, setRegistered] = useState(AppState.Unregister);

  const register = async () => {
    if (registered !== AppState.Unregister) {
      return;
    }
    try {
      Pedometer.register();

      Pedometer.addListener('step', (event: SensorEvent) => {
        console.log('event fired: ', event.steps);

        setStepCount(event.steps)
      })

      Pedometer.addListener('activate', (event: SensorEvent) => {
        console.log('activate:', event.steps);

        setStepCount(event.steps);

        setRegistered(AppState.Registered);
      })

      setRegistered(AppState.Registering);
    } catch (e) {
      console.log(e);
    }
  }

  const content = () => {
    switch (registered) {
      case AppState.Unregister: return "Unregistered";
      case AppState.Registering: return "Registering...";
      case AppState.Registered: return "Current steps count: " + stepCount.toString(10);
    }
  }

  return (
    <IonPage id="home-page">
      <IonHeader>
        <IonToolbar>
          <IonTitle>Step Tracker</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent fullscreen>
        <IonGrid style={{ height: "100%", width: "100%" }}>
          <IonRow style={{ height: "80%" }} class='ion-align-items-center ion-justify-content-center'>
            <IonCol size='auto'>
              {content()}
            </IonCol>
          </IonRow>
          <IonRow style={{ height: "20%" }} class='ion-align-items-center ion-justify-content-center'>
            <IonCol size='auto'>
              <IonButton onClick={() => register()}>
                Register
              </IonButton>
            </IonCol>
          </IonRow>
        </IonGrid>
      </IonContent>
    </IonPage>
  );
};

export default Home;
