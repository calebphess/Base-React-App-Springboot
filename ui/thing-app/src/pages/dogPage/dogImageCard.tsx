import { makeStyles } from 'tss-react/mui';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import { CardMedia, CircularProgress } from '@mui/material';
import { useAppSelector } from '../../hooks'
import { IAppState } from '../../store/store';
import { loadDogAction } from '../../actions/external/dogActions';
import Dog from '../../models/dogModel';

const useStyles = makeStyles()(() => ({
  root: {
    flexWrap: 'wrap',
    height: 300,
    width: 300,
    minHeight: 300,
    minWidth: 300
  },
  cardContent: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    marginTop: '40%'
  },
  image: {
    height: 0,
    paddingTop: '0%'
  },
  img: {
    width: 300,
    height: 300
  }
}));

export default function DogImageCard() {
  //this object represents the classes that we defined 
  const { classes } = useStyles();

  //here we declare what we want to take from the redux store with the useSelector() hook
  //every time one of these properties is updated on the store, our component will re-render to reflect it
  const dog:Dog = useAppSelector((state: IAppState) => state.dogState.dog);
  const loading:boolean = useAppSelector((state: IAppState) => state.dogState.loading);
  const errorMessage:string = useAppSelector((state: IAppState) => state.dogState.errorMessage);

  //here we define simple stateless components for the card image and error messages
  //notice how we dispatch the call to end the loading of the image based on the img element's onLoad event 
  const cardImage = (src: string) => 
    <CardMedia className={classes.image}>
      <img alt="doggo" className={classes.img} onLoad={() => loadDogAction(false)} src={src}></img>
    </CardMedia>

  const cardError = (message: string) => <Typography color="error">{message}</Typography>

  return (
    <Card className={classes.root}>
        {dog.image? cardImage(dog.image) : ''}
        <CardContent className={classes.cardContent}>
        {!loading && !dog.image && !errorMessage? <Typography>Waiting for doggo...</Typography> : ''}
        {loading? <CircularProgress size="80px" color="primary"></CircularProgress> : ''}
        {errorMessage && !dog.image && !loading? cardError(errorMessage) : ''}
        </CardContent>
    </Card>
  );
}