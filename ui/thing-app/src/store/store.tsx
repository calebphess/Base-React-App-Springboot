/*  Imports from Redux:
 applyMiddleware: Applies middleware to the dispatch method of the Redux store
 combineReducers: Merges reducers into one
 createStore: Creates a Redux store that holds the state tree
 Store: The TS Type used for the store, or state tree
 */
 import { applyMiddleware, combineReducers, createStore, Store } from 'redux';
 /*  Thunk
 Redux Thunk middleware allows you to write action creators that return a function instead of an action. The thunk can be used to delay the dispatch of an action, or to dispatch only if a certain condition is met. The inner function receives the store methods dispatch and getState as parameters.
 */
 import thunk from 'redux-thunk';
 // Import reducers and state type
 import { IDogState, dogReducer } from '../reducers/dogReducer';
 import { IAuthTokenState, authTokenReducer } from '../reducers/authTokenReducer';

 
 // Create an interface for the application state
 export interface IAppState {
   dogState: IDogState,
   authTokenState: IAuthTokenState
 }
 
 // Create the root reducer
 const rootReducer = combineReducers<IAppState>({
  dogState: dogReducer,
  authTokenState: authTokenReducer
 });
 
 // Create a configure store function of type `IAppState`
 // TODO: figure out the right way to do this with the new non-deprecated configureStore function from redux
 export default function configureStore(): Store<IAppState, any> {
   const store = createStore(rootReducer, undefined, applyMiddleware(thunk));
   return store;
 }


 const store = configureStore();

 export type AppDispatch = typeof store.dispatch

 export type RootState = ReturnType<typeof store.getState>