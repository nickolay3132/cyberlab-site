package store.technocyberlab.cyberlabsite.core.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import store.technocyberlab.cyberlabsite.core.entities.Release

@Repository
interface ReleaseRepository : CrudRepository<Release, Long>