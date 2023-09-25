
import red from '@mui/material/colors/red';
import { createTheme } from '@mui/material/styles';

// A custom theme for this app
const theme = createTheme({
  palette: {
    primary: {
      main: '#554596',
    },
    secondary: {
      main: '#6272a4',
    },
    error: {
      main: red.A400,
    },
    background: {
      default: '#282a36',
    },
  },
});

export default theme;