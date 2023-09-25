import { ActionCreator, Dispatch } from 'redux';
import { ThunkAction } from 'redux-thunk';
import { IDogState } from '../../reducers/dogReducer';
import DogModel from '../../models/dogModel';

export enum DogActionTypes {
    RANDOM_DOG = 'RANDOM_DOG',
    LOAD_DOG = 'LOAD_DOG',
    ERROR = 'ERROR'
}

export interface IRandomDogAction {
    type: DogActionTypes.RANDOM_DOG;
    dog: DogModel;
}

export interface ILoadDogAction {
    type: DogActionTypes.LOAD_DOG;
    loading: boolean;
}

export interface IErrorAction {
    type: DogActionTypes.ERROR;
    errorMessage: string;
}
export type DogActions = IRandomDogAction | ILoadDogAction | IErrorAction;

/*<Promise<Return Type>, State Interface, Type of Param, Type of Action> */
export const RandomDogAction: ActionCreator<ThunkAction<Promise<any>, IDogState, null, IRandomDogAction>> = (dogBreed: string) => {
    return async (dispatch: Dispatch) => {
        try {
            let result = await (await fetch(`https://dog.ceo/api/breed/${dogBreed}/images/random`)).json();
            if (result.status !== 'success')
                throw new Error(result.message);

            let dog = new DogModel();
            dog.image = result.message;

            dispatch({type: DogActionTypes.RANDOM_DOG, dog: dog});
        } catch (err: any) {
            console.error(err);
            let dog = new DogModel();
            dispatch({type: DogActionTypes.RANDOM_DOG, dog: dog});
            dispatch({type: DogActionTypes.ERROR, errorMessage: err.message});
        };

        dispatch({type: DogActionTypes.LOAD_DOG, loading: false});
    };
};

export const loadDogAction: ActionCreator<ThunkAction<any, IDogState, null, ILoadDogAction>> = (shouldLoad: boolean) => 
    (dispatch: Dispatch) => dispatch({type: DogActionTypes.LOAD_DOG, loading: shouldLoad})