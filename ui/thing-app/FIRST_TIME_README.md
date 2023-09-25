# First time React Redux Typscript Material UI Project

## Notes

### Initial Setup
Assuming you have installed node and npm/npx
This is based but a slight modification of (this link)[https://levelup.gitconnected.com/how-to-setup-a-react-spa-with-typescript-redux-and-material-ui-99a1e1ec7d54]
1. Create a new react app with typescript
  - `npx create-react-app my-app --template typescript`
2. Install the required libraries
  - `npm install --save @mui/material @emotion/react @emotion/styled`
    - This is the material ui library specific install for React 18
    - `@mui/material` is the core library so you will need to replace anything that says `@material-ui/core` with that
  - `npm install --save react-router-dom redux react-redux redux-thunk`
  - `npm install --save-dev @types/react-router-dom @types/material-ui @types/redux @types/react-redux`
    - Need to read up more on what this is
  - ONLY do this if you are getting startup errors
    - `npm install --save-dev @babel/plugin-proposal-private-property-in-object`
  - `npm install --save tss-react`
    - This replaces the deprecated material-ui/styles library
  - `npm install --save @reduxjs/toolkit`
    - used in store to replace the deprecated `createStore` method

### React SAP Redux/Material/Typescript Notes
NOTE: This is specific to React Version 18.2.0
#### Actions
  - Actions can be used to define the web calls that we make for each api sort of like mapping api calls to methods
  - Each action should always have a LoadAction and an ErrorAction
    - LoadAction is used to track whether or not the api has returned a response yet from the async fetch call
    - ErrorAction handle state when error responses are returned from the API
#### Reducers
#### Components
  - MORE READING: research the useEffect() hook react for access to common react lifecycle methods
#### Pages
#### Typescript
  - if you are getting a type error for a variable, you may have to define it as any or the type that it is
    - `method(parameter: any)`
    - `method(parameter: SomeType)`
    - `const [state, setState] = useState<any>({})`
    - `const [state, setState] = useState<SomeType>({})`