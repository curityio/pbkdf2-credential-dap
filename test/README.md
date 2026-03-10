## Testing the Plugin

You can easily run a complete setup to test the implementation of the plugin.

To run the test, follow these steps:

1. Make sure you have docker and jq installed.
2. Set the `LICENSE_FILE_PATH` environment variable to point to your Curity Identity Server license file, e.g.:

```bash
export LICENSE_FILE_PATH=~/licenses/license.json
```

3. Build the plugin. From the root directory run `./gradlew build`.
4. Run `./test/docker_run.sh`. This will start the Curity Identity Server with the plugin already configured as described in the main [README](../README.rst). 
5. Run `./test/import_data.sh` to create a test account with the properly hashed password.
6. Open the [oauth.tools](https://oauth.tools) app and run a code flow using:
    - client ID: `client-one`
    - client secret: `Password1`
    - username:`dana@demo.example`
    - user password: `1234`
