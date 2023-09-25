import { useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { CardHeader, TextField, CircularProgress } from '@mui/material';
import { useAppSelector, useAppDispatch } from '../../hooks'
import { RandomDogAction, loadDogAction } from '../../actions/external/dogActions';
import { IAppState } from '../../store/store';

const useStyles = makeStyles()(() => ({
  root: {
    display: 'flex',
    flexDirection: 'column',
    width: 300,
    height: 300,
    minHeight: 300,
    minWidth: 300,
    alignSelf: 'middle',
    justifySelf: 'start'

  },
  content: {
    display: 'flex',
    flexDirection: 'column',
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    marginTop: '50'
  },
  button: {
    marginTop: '40px',
    height: '50',
    width: '90%'
  },
  input: {
    width: '90%'
  },
  title: {
    fontSize: 14,
  }
}));

export default function DogFormCard() {
  //this object represents the classes that we defined 
  const { classes } = useStyles();
  //this hook allows us to access the dispatch function
  const dispatch = useAppDispatch();
  //the useState() hook allows our component to hold its own internal state
  //the dogName property isn't going to be used anywhere else, so there's no need to hold it on the redux store
  const [dogName, setDogName] = useState('');
  //here we watch for the loading prop in the redux store. every time it gets updated, our component will reflect it
  const isLoading = useAppSelector((state: IAppState) => state.dogState.loading);

  //a function to dispatch multiple actions
  const getDog = () => {
    dispatch(loadDogAction(true));
    dispatch(RandomDogAction(dogName));
  }

  return (
    <Card className={classes.root}>
      <CardHeader title={<Typography variant="h5" component="h2">Find Doggo</Typography>}></CardHeader>
      <CardContent className={classes.content}>
        <TextField 
        onChange={(e) => setDogName(e.target.value)} 
        className={classes.input} 
        label="Type a dog breed..."
        variant="outlined"
        inputProps={{ maxLength: 20 }}></TextField>
        <Button onClick={() => getDog()} className={classes.button} variant="contained" size="large" color="primary"> 
          {isLoading? <CircularProgress color="secondary"></CircularProgress> : <Typography>get {dogName} doggo</Typography>}
        </Button>
      </CardContent>
    </Card>
  );
}