import { ActionCreator, Dispatch } from 'redux';
import { ThunkAction } from 'redux-thunk';
import { IAuthTokenState } from '../../reducers/authTokenReducer';
import AuthTokenModel from '../../models/authTokenModel';
import { useNavigate } from 'react-router-dom';


export enum AuthTokenActionTypes {
    LOGIN = 'LOGIN',
    LOAD_LOGIN = 'LOAD_LOGIN',
    ERROR = 'ERROR'
}

export interface IAuthTokenAction {
    type: AuthTokenActionTypes.LOGIN;
    authToken: AuthTokenModel;
}

export interface ILoadAuthTokenAction {
    type: AuthTokenActionTypes.LOAD_LOGIN;
    loading: boolean;
}

export interface IErrorAction {
    type: AuthTokenActionTypes.ERROR;
    errorMessage: string;
}
export type AuthTokenActions = IAuthTokenAction | ILoadAuthTokenAction | IErrorAction;

/*<Promise<Return Type>, State Interface, Type of Param, Type of Action> */
export const AuthTokenAction: ActionCreator<ThunkAction<Promise<any>, IAuthTokenState, null, IAuthTokenAction>> = (userLoginId: string, userPassword: string, redirect:() => void) => {
    return async (dispatch: Dispatch) => {
        try {
            // get date to calculate token expiration
            const now = Date.now();

            // query to get the token
            let result = await (
                await fetch("https://localhost:8443/thingapp/api/auth/token", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ userLoginId, userPassword })
            }));

            if (result.ok) {
                let body = await result.json();

                let authToken = new AuthTokenModel();
                authToken.accessToken = body.accessToken;
                authToken.tokenType = body.tokenType;
                authToken.expires = new Date(now + (body.expires * 1000));

                dispatch({authToken: authToken, type: AuthTokenActionTypes.LOGIN });
                dispatch({type: AuthTokenActionTypes.LOAD_LOGIN, loading: false});
                
                // redirect the user if the option was provided
                if (redirect) {
                    redirect();
                }
            }
            else {
                throw new Error(result.status.toString());
            }
            
        } catch (err: any) {
            console.error(err);
            dispatch({type: AuthTokenActionTypes.ERROR, error: true});
            dispatch({type: AuthTokenActionTypes.LOAD_LOGIN, loading: false});
        };
    };
};

export const loadAuthTokenAction: ActionCreator<ThunkAction<any, IAuthTokenState, null, ILoadAuthTokenAction>> = (shouldLoad: boolean) => 
    (dispatch: Dispatch) => dispatch({type: AuthTokenActionTypes.LOAD_LOGIN, loading: shouldLoad})