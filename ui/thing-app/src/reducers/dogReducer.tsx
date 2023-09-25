import { Reducer } from 'redux';
import { DogActionTypes, DogActions } from '../actions/external/dogActions';
import DogModel from '../models/dogModel';

export interface IDogState {
    dog: DogModel;
    loading: boolean;
    errorMessage: string;
}

const initialDogState: IDogState = {
    dog: new DogModel(),
    loading: false,
    errorMessage: ''
};

export const dogReducer: Reducer<IDogState, DogActions> = (
    state = initialDogState,
    action
  ) => {
    switch (action.type) {
      case DogActionTypes.RANDOM_DOG: {
        return {
          ...state,
          dog: action.dog
        };
      }
      case DogActionTypes.LOAD_DOG: {
        return {
          ...state,
          loading: action.loading
        };
      }
      case DogActionTypes.ERROR: {
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