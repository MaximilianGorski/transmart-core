PGHOST=
PGPORT=5432
PGDATABASE=transmart
PGUSER=postgres
PGPASSWORD=

PGSQL_BIN="sudo -E -u postgres /usr/bin/"
TABLESPACES=/var/lib/postgresql/tablespaces/
KETTLE_JOBS_PSQL=<?= realpath(__DIR__), "/tranSMART-ETL/Postgres/GPL-1.0/Kettle/Kettle-ETL/", "\n" ?>
KITCHEN=<?= realpath(__DIR__), "/data-integration/kitchen.sh", "\n" ?>

PATH=<?= realpath(__DIR__) ?>:$PATH

export PGHOST PGPORT PGDATABASE PGUSER PGPASSWORD TABLESPACES PGSQL_BIN \
	KETTLE_JOBS_PSQL KITCHEN PATH
