package consulting.hippone.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import javax.sql.DataSource

fun hikari(dataSource: Env.DataSource): HikariDataSource =
    HikariDataSource(
        HikariConfig().apply {
            jdbcUrl = dataSource.url
            driverClassName = dataSource.driver
            username = dataSource.username
            password = dataSource.password
            validate()
        }
    )