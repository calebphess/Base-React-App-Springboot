import { Reducer } from 'redux';
import { AuthTokenActionTypes, AuthTokenActions } from '../actions/server/authTokenActions';

export interface IAuthTokenState {
    accessToken: string;
    tokenType: string;
    expires: string;
    loading: boolean;
    error: boolean;
}

const initialAuthTokenState: IAuthTokenState = {
  accessToken: '',
  tokenType: '',
  expires: '',
  loading: false,
  error: false
};

export const authTokenReducer: Reducer<IAuthTokenState, AuthTokenActions> = (
    state = initialAuthTokenState,
    action
  ) => {
    switch (action.type) {
      case AuthTokenActionTypes.LOGIN: {
        return {
          ...state,
          authToken: action.authToken
        };
      }
      case AuthTokenActionTypes.LOAD_LOGIN: {
        return {
          ...state,
          loading: action.loading
        };
      }
      case AuthTokenActionTypes.ERROR: {
        return {
          ...state,
          errorMessage: action.errorMessage,
          image: ''
        }
      }
      default:
        return state;
    }
  };