const BASE_URL = 'https://localhost:8446/api';
const FILE_PATH = BASE_URL + '/presentation';
//프론트 3000 - 백엔드 8446포트에서 테스트 할 때 사용하세요.
const GOOGLE_OAUTH_URL =
  'https://localhost:8446/oauth2/authorize/google?redirect_uri=https://localhost:3000/oauth2/redirect';
//8443포트
// const GOOGLE_OAUTH_URL =
//   'https://localhost:8446/oauth2/authorize/google?redirect_uri=https://localhost:8443/oauth2/redirect';
<<<<<<< Updated upstream

export { BASE_URL, GOOGLE_OAUTH_URL, FILE_PATH };
=======
export { BASE_URL, GOOGLE_OAUTH_URL };
>>>>>>> Stashed changes
