package ru.ntdv.proicis.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ntdv.proicis.constant.ThemeState;
import ru.ntdv.proicis.crud.model.Season;
import ru.ntdv.proicis.crud.model.Theme;
import ru.ntdv.proicis.crud.model.User;
import ru.ntdv.proicis.crud.repository.FileRepository;
import ru.ntdv.proicis.crud.repository.ThemeRepository;
import ru.ntdv.proicis.graphql.input.ThemeInput;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public
class ThemeService {

@Autowired
private ThemeRepository themeRepository;
@Autowired
private FileRepository fileRepository;

@Autowired
private UserService userService;

public
List<Theme> getAllThemes() {
    return themeRepository.findAll();
}

public
Set<Theme> getMentorThemes(final User mentor) {
    return themeRepository.findAllByMentorsContains(mentor);
}

public
Theme createTheme(final ThemeInput themeInput, final User author, final Set<User> mentors, final List<Season> seasons) {
    return themeRepository.saveAndFlush(new Theme(themeInput,
                                                  fileRepository.getReferenceById(themeInput.getPresentationSlide()), author,
                                                  mentors, seasons));
}

public
Theme updateTheme(final Long themeId, final ThemeInput themeInput, final User author) {
    final Theme theme = getTheme(themeId);
    theme.setTitle(themeInput.getTitle());
    theme.setDescription(themeInput.getDescription());
    theme.setPresentationSlide(fileRepository.getReferenceById(themeInput.getPresentationSlide()));
    theme.setHardness(themeInput.getHardness());
    theme.setSkills(themeInput.getSkills());
    theme.setAuthor(author);
    return themeRepository.saveAndFlush(theme);
}

public
Theme getTheme(final Long themeId) throws NoSuchElementException {
    return themeRepository.findById(themeId).orElseThrow(() -> new NoSuchElementException("No such theme found."));
}

public
void deleteTheme(final Long themeId) {
    themeRepository.deleteById(themeId);
}

public
Theme setAuthorToTheme(final Long themeId, final Long userId) {
    return themeRepository.setAuthorToTheme(themeId, userId);
}

public
Theme attachMentorsToTheme(final Long themeId, final Set<Long> mentorIds) {
    final Theme theme = getTheme(themeId);
    final var mentors = theme.getMentors();
    mentorIds.forEach(mentorId -> mentors.add(userService.getUserById(mentorId)));
    return themeRepository.saveAndFlush(theme);
}

public
Theme detachMentorsFromTheme(final Long themeId, final Set<Long> mentorIds) {
    final Theme theme = getTheme(themeId);
    final var mentors = theme.getMentors();
    mentorIds.forEach(mentorId -> mentors.remove(userService.getUserById(mentorId)));
    return themeRepository.saveAndFlush(theme);
}

public
Theme changeThemeState(final Long themeId, final ThemeState state) {
    return themeRepository.changeThemeState(themeId, state);
}
}
