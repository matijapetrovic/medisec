package com.medisec.adminservice.domain.template;

import com.medisec.adminservice.domain.extension.CertificateExtensions;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="template")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Embedded
    private CertificateExtensions extensions;
}
