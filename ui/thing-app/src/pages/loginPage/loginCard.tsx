import { useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Link from '@mui/material/Link';
import Typography from '@mui/material/Typography';
import { CardHeader, TextField, CircularProgress, Avatar } from '@mui/material';
import { useAppSelector, useAppDispatch } from '../../hooks'
import { AuthTokenAction, loadAuthTokenAction } from '../../actions/server/authTokenActions';
import { IAppState } from '../../store/store';
import { useNavigate } from 'react-router-dom';


const useStyles = makeStyles()(() => ({
  root: {
    width: 400,
    height: 480,
    alignSelf: 'middle',
    justifySelf: 'start'
  },
  content: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    marginTop: '2vh'
  },
  button: {
    marginTop: '15px',
    height: '7vh',
    width: '90%'
  },
  logo: {
    marginTop: '0px',
    marginBottom: '30px'
  },
  actionErrorMessage: {
    color: 'error'
  },
  signupText: {
    color: '#111111',
    marginTop: '10px',
    '&:hover': {
      cursor: 'pointer'
    }
  },
  signupLink: {
    color: 'primary'
  },
  input: {
    width: '90%',
    marginBottom: '10px'
  },
  bullet: {
    display: 'inline-block',
    margin: '0 2px',
    transform: 'scale(0.8)'
  },
  title: {
    fontSize: 14,
    marginTop: '10px',
    paddingBottom: '0px'
  },
  pos: {
    marginBottom: 12
  }
}));


export default function LoginCard() {

  // set up router navigation
  const navigate = useNavigate();

  // grab and use the CSS classes from above
  const { classes } = useStyles();

  // use dispatch to connect us to the controller
  const dispatch = useAppDispatch();

  // define internal state
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [emailError, setEmailError] = useState(false);
  const [passwordError, setPasswordError] = useState(false);

  // track when calls are loading for the login method
  const isLoading = useAppSelector((state: IAppState) => state.authTokenState.loading);

  // track any potential action error messages
  const actionError = useAppSelector((state: IAppState) => state.authTokenState.error);

  // a function to dispatch multiple actions
  const doLogin = () => {
    var formErrors = false;

    setEmailError(!email)
    setPasswordError(!password)

    if (!email || !password) {
      formErrors = true;
    }

    if (!formErrors) {
      dispatch(loadAuthTokenAction(true));
      dispatch(AuthTokenAction(email, password, goHome));
    }

  }

  const goHome = () => {
    navigate("/");
  }

  // function to route the user to the signup page
  const goToSignup = () => {
    navigate("/signup");
  }

  return (
    <Card className={classes.root}>
      <CardHeader className={classes.title} title={<Typography variant="h4" component="h2">Welcome</Typography>}></CardHeader>
      <CardContent className={classes.content}>
        <Avatar className={classes.logo} sx={{ bgcolor: "#282a36", width: 100, height: 100 }} src="/static/images/nc_icon.png"></Avatar>
        {actionError? <Typography variant="body1" className={classes.actionErrorMessage}>Invalid AuthToken Credentials</Typography> : ''}
        <TextField 
          onChange={(e) => setEmail(e.target.value)} 
          className={classes.input} 
          label="Email" 
          variant="outlined"
          error={!!emailError}
          required>
        </TextField>
        <TextField
          onChange={(e) => setPassword(e.target.value)} 
          className={classes.input} 
          label="Password" 
          variant="outlined"
          error={!!passwordError}
          type="password"
          required>
        </TextField>
        <Button onClick={() => doLogin()} className={classes.button} variant="contained" size="large" color="primary"> 
          {isLoading? <CircularProgress color="secondary"></CircularProgress> : <Typography>Login</Typography>}
        </Button>
        <Typography variant="body1" className={classes.signupText}>New here? <Link onClick={() => goToSignup()} className={classes.signupLink}>Sign up</Link></Typography>
      </CardContent>
    </Card>
  );
}