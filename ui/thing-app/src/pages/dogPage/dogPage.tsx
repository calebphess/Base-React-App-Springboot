import { makeStyles } from 'tss-react/mui';
import DogFormCard from './dogFormCard';
import { Container } from '@mui/material';
import DogImageCard from './dogImageCard';

const useStyles = makeStyles()(() => ({
  root: {
    textAlign: "center",
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    height: '100vh'
  }
}));

const DogPage = () => {
  const { classes } = useStyles();

  return (
    <Container className={classes.root}>
        <DogFormCard/>
        <DogImageCard/>
    </Container>
  );
}

export default DogPage;