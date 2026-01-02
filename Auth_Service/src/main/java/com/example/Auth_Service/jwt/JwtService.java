package main.java.com.example.Auth_Service.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {
	private final String issuer;
	private final long ttlSeconds;
	private final byte[] secret;

	public JwtService(
			@Value("${app.jwt.issuer:hospital-auth}") String issuer,
			@Value("${app.jwt.ttl-seconds:3600}") long ttlSeconds,
			@Value("${app.jwt.secret}") String secret
	) {
		this.issuer = issuer;
		this.ttlSeconds = ttlSeconds;
		this.secret = secret.getBytes(StandardCharsets.UTF_8);
	}

	public String issueToken(String subject) {
		Instant now = Instant.now();
		Instant exp = now.plusSeconds(ttlSeconds);

		JWTClaimsSet claims = new JWTClaimsSet.Builder()
				.issuer(issuer)
				.subject(subject)
				.issueTime(Date.from(now))
				.expirationTime(Date.from(exp))
				.claim("typ", "access")
				.build();

		SignedJWT jwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);
		try {
			jwt.sign(new MACSigner(secret));
		} catch (JOSEException e) {
			throw new IllegalStateException("Failed to sign JWT", e);
		}
		return jwt.serialize();
	}

	public Instant expiresAt() {
		return Instant.now().plusSeconds(ttlSeconds);
	}
}
