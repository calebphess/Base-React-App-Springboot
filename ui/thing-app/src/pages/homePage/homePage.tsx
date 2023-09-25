import { makeStyles } from 'tss-react/mui';
import { Container, Typography, Button } from '@mui/material';

const useStyles = makeStyles()(() => ({
  root: {
    display: 'flex',
    flexDirection: 'column',
    textAlign: "center",
    alignItems: 'center ',
    justifyContent: 'top',
    height: '100vh',
  },

  title: {
    '&:hover': {
      transform: 'scale(1.1)',
      transition: 'all 1s ease',
      background: 'linear-gradient(to left, #e3e793 40%, #96c9e6, #a373ec 60%)',
      WebkitBackgroundClip: 'text',
    },
    fontSize: '70px',
    fontWeight: 'bold',
    fontStyle: 'italic',
    background: 'linear-gradient(to right, #e3e793 40%, #96c9e6, #a373ec 60%)',
    WebkitBackgroundClip: 'text',
    color: 'transparent',
    transition: 'all 5s ease',
    padding: '10px',
    height: '110px',
    marginTop: '30px'
  },
  bio: {
    '& span': {
      display: `inline-block`,
      transition: `2s all`,
    },
    '& span:hover': {
      transform: 'scale(1.1)',
      color: 'var(--hover-color)',
      transition: `0.25s all`,
    },
    textAlign: 'justify',
    fontWeight: 'bold',
    color: '#eeeeee',
    fontFamily: 'Arial, sans-serif',
    fontSize: '20px',
    padding: '20px',
    margin: '30px 0',
    maxWidth: '800px'
  },
  button: {
    marginTop: '30px',
    height: '7vh',
    width: '400px'
  }
}));

const HomePage = () => {
  const { classes } = useStyles();

  document.querySelectorAll('span').forEach(span => {
    // have to type cast span to call style on it
    var spanElement = span as HTMLElement;
    spanElement.addEventListener('mouseover', function() {
        const colors = ['#e3e793', '#96c9e6', '#a373ec'];
        const color = colors[Math.floor(Math.random() * colors.length)];
        spanElement.style.setProperty('color', color);
    });
});

  return (
    <Container className={classes.root}>
        <Typography className={classes.title} variant="h1" component="h1">//NoComment</Typography>
        <Typography variant="body1" className={classes.bio}>
          <span>We</span> <span>are</span> <span>a</span> <span>company</span> <span>that</span> <span>provides</span> <span>material</span> <span>to</span> <span>train</span> <span>new</span> <span>software</span> <span>developers</span> <span>as</span> <span>well</span> <span>as</span> <span>improve</span> <span>the</span> <span>habits</span> <span>and</span> <span>coding</span> <span>practices</span> <span>of</span> <span>existing</span> <span>software</span> <span>engineers</span> <span>to</span> <span>help</span> <span>make</span> <span>the</span> <span>world</span> <span>a</span> <span>better</span> <span>place.</span> <span>We</span> <span>do</span> <span>this</span> <span>by</span> <span>offering</span> <span>both</span> <span>material</span> <span>and</span> <span>training.</span>
        </Typography>
        <Button onClick={() => null} className={classes.button} variant="contained" size="large" color="primary"> 
          <Typography>Learn More</Typography>
        </Button>
    </Container>
  );
}

export default HomePage;