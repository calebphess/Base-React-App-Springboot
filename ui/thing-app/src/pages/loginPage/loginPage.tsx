import { makeStyles } from 'tss-react/mui';
import { Container } from '@mui/material';
import LoginCard from './loginCard';

const useStyles = makeStyles()(() => ({
  root: {
    textAlign: "center",
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    height: '100vh'
  }
}));

const LoginPage = () => {
  const { classes } = useStyles();

  return (
    <Container className={classes.root}>
        <LoginCard/>
    </Container>
  );
}

export default LoginPage;