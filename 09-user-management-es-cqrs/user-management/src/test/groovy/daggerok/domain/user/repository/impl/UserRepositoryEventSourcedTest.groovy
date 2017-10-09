package daggerok.domain.user.repository.impl

import daggerok.domain.user.User
import daggerok.domain.user.repository.RecreatableStateFromSpecificTime
import daggerok.domain.user.repository.UserRepository
import spock.lang.Specification

import java.time.Instant

import static daggerok.domain.user.UserStatus.ACTIVATED
import static daggerok.domain.user.UserStatus.DEACTIVATED
import static daggerok.domain.user.UserStatus.INITIALIZED

class UserRepositoryEventSourcedTest extends Specification {

  UserRepository repository = new UserRepositoryEventSourced()

  def "should be able to save and load users"() {
    given:
      UUID id = UUID.randomUUID()
    and:
      User user = new User(id)
    and:
      user.activate()
    and:
      user.changeNickname("max")
    and:
      repository.save(user)
    when:
      User userFromDb = repository.find(id)
    then:
      userFromDb != null
    and:
      userFromDb.isActivated()
    and:
      userFromDb.nickname == "max"
  }

  def "should be able to save and load historical user state"() {
    given:
      UUID id = UUID.randomUUID()
    and:
      User user = new User(id)
      repository.save(user)
      Instant stop1 = Instant.now()
      sleep(1)
    and:
      user.activate()
      repository.save(user)
      Instant stop2 = Instant.now()
      sleep(1)
    and:
      user.changeNickname("max")
      repository.save(user)
      Instant stop3 = Instant.now()
      sleep(1)
    and:
      user.changeNickname("fax")
      user.deactivate()
      repository.save(user)
    when:
      User latest = repository.find(id)
      RecreatableStateFromSpecificTime historicalRepository = repository as RecreatableStateFromSpecificTime
      User v3 = historicalRepository.findFromHistory(id, stop3)
      User v2 = historicalRepository.findFromHistory(id, stop2)
      User v1 = historicalRepository.findFromHistory(id, stop1)
    then:
      latest.state == DEACTIVATED
      latest.nickname == "fax"
    and:
      v3.state == ACTIVATED
      v3.nickname == "max"
    and:
      v2.state == ACTIVATED
      v2.nickname == "anonymous"
    and:
      v1.nickname == "anonymous"
      v1.state == INITIALIZED
  }
}
