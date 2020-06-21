#!/usr/local/bin/env groovy
def appilcation(cmd, type = 'shell') {
  if (type == 'shell') {
    sh "${cmd}"
  }
}
