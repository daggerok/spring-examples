package daggerok.domain

import daggerok.domain.user.User
import spock.lang.Specification

class UserTest extends Specification {

  User user = new User(UUID.randomUUID())

  def 'activated user cannot be activated'() {
    given:
      user.activate()
    when:
      user.activate()
    then:
      thrown(IllegalStateException)
  }

  def 'deactivated user cannot be deactivated'() {
    given:
      user.deactivate()
    when:
      user.deactivate()
    then:
      thrown(IllegalStateException)
  }

  def 'activated user can change username'() {
    given:
      user.activate()
    when:
      user.changeNickname('new nick')
    then:
      user.nickname == 'new nick'
  }

  def 'deactivated user cannot change username'() {
    given:
      user.deactivate()
    when:
      user.deactivate()
    then:
      thrown(IllegalStateException)
  }
}
