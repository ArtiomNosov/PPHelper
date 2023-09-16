import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import { configureStore } from '@reduxjs/toolkit';
import { Provider } from 'react-redux';

const defaultState = {
    waitingForServer: 0,
    showingSuccessful: 0,
    error: "",
}

function reduxReducer(state = defaultState, action) {
    switch (action.type) {
        case "server/waiting":
            return {...state, waitingForServer: 1};
        case "server/gotResponse":
            return {...state, waitingForServer: 0};
        case "server/success":
            return {...state, showingSuccessful: 1};
        case "server/successOK":
            return {...state, showingSuccessful: 0};
        case "server/error":
            return {...state, error: action.payload};
        case "server/errorOK":
            return {...state, error: ""};

        default:
            return state;
    }
}

const store = configureStore({reducer: reduxReducer});

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <Provider store={store}>
        <App />
    </Provider>
);