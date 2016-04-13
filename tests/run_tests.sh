#!/bin/bash
# Run the tests and compare with expected output

function run_apppal() {
  local policy="${1}"
  rm -f "${policy}.output"
  java -jar ../AppPAL.jar -p "${policy}.policy" -q "${policy}.queries" >"${policy}.output" 2>&1
}

function pass() {
  local policy="${1}"
  printf "✅  %s\n" "${policy}"
}

function fail() {
  local policy="${1}"
  shift
  local reason="${*}"
  printf "❌  %s: %s\n" "${policy}" "${reason}"
}

function check_test() {
  local policy="${1}"
  if [ ! -r "${policy}.output" ]; then run_apppal "${policy}"; fi
  if [ ! -r "${policy}.output" ]; then
    fail "${policy}" "could not produce correct output"
  fi
  diff "${policy}.correct" "${policy}.output" >/dev/null 2>&1
  if [ ${?} -ne 0 ]; then
    fail "${policy}" "test failed"
    diff -u "${policy}.correct" "${policy}.output"
  else
    pass "${policy}"
  fi
}

function main() {
  for test_output in *.queries; do
    testname="$(basename "${test_output}" .queries)"
    policy="${testname}.policy"
    queries="${testname}.queries"

      if [ ! -r "${policy}" ];  then fail "${testname}" "missing policy for: ${policy}";
    elif [ ! -r "${queries}" ]; then fail "${testname}" "missing queries for: ${queries}";
    else check_test "${testname}"
    fi
  done
}

main
