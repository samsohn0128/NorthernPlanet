// const BASE_URL = 'https://localhost:8446/api';
// 배포 설정
const BASE_URL = 'https://k5a204.p.ssafy.io/api';
const FILE_PATH = BASE_URL + '/presentation';
//프론트 3000 - 백엔드 8446포트에서 테스트 할 때 사용하세요.
// const GOOGLE_OAUTH_URL =
//   'https://localhost:8446/oauth2/authorize/google?redirect_uri=https://localhost:3000/front/oauth2/redirect';
//8443포트
const GOOGLE_OAUTH_URL =
  'https://localhost:8446/oauth2/authorize/google?redirect_uri=https://localhost:8443/front/oauth2/redirect';
//배포
// const GOOGLE_OAUTH_URL =
//   'https://k5a204.p.ssafy.io/oauth2/authorize/google?redirect_uri=https://k5a204.p.ssafy.io/front/oauth2/redirect';

export { BASE_URL, GOOGLE_OAUTH_URL, FILE_PATH };
