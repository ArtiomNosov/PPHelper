package ru.ntdv.proicis.crud.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import ru.ntdv.proicis.crud.contract.FileAccessPolicy;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(generator = "org.hibernate.id.UUIDGenerator")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String originalName;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private User owner;

    @ElementCollection
    private Set<FileAccessPolicy> accessPolicy;

    public File(final String originalName, final User owner, final FileAccessPolicy... fileAccessPolicy) {
        this(originalName, owner, Arrays.stream(fileAccessPolicy).collect(Collectors.toSet()));
    }

    public File(final String originalName, final User owner, final Set<FileAccessPolicy> fileAccessPolicy) {
        this.originalName = originalName;
        this.owner = owner;
        this.accessPolicy = fileAccessPolicy;
    }
}
