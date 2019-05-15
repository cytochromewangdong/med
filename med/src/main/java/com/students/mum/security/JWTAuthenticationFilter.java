package com.students.mum.security;

public class JWTAuthenticationFilter  {
//extends UsernamePasswordAuthenticationFilter {
//	private AuthenticationManager authenticationManager;
//
//	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
//		this.authenticationManager = authenticationManager;
//	}
//
//	@Override
//	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
//			throws AuthenticationException {
//		try {
//			String userName = req.getParameter("username");
//			String password = req.getParameter("password");
//			return authenticationManager
//					.authenticate(new UsernamePasswordAuthenticationToken(userName, password, new ArrayList<>()));
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	@Override
//    protected void successfulAuthentication(HttpServletRequest req,
//                                            HttpServletResponse res,
//                                            FilterChain chain,
//                                            Authentication auth) throws IOException, ServletException {
//
//        String token = JWT.create()
//                .withSubject(((LoginUser) auth.getPrincipal()).getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .sign(HMAC512(SECRET.getBytes()));
//        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
//    }
}