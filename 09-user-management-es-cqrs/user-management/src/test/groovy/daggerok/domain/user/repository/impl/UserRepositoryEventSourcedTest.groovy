package daggerok.domain.user.repository.impl

import daggerok.domain.user.User
import daggerok.domain.user.repository.UserRepository
import spock.lang.Specification

class UserRepositoryEventSourcedTest extends Specification {

  UserRepository repository = new UserRepositoryEventSourced()

  def 'should be able to save and load users'() {
    given:
      UUID id = UUID.randomUUID()
    and:
      User user = new User(id)
    and:
      user.activate()
    and:
      user.changeNickname('max')
    and:
      repository.save(user)
    when:
      User userFromDb = repository.find(id)
    then:
      userFromDb != null
    and:
      userFromDb.isActivated()
    and:
      userFromDb.nickname == 'max'
  }
}
