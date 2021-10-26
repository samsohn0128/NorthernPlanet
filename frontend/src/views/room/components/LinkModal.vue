<template>
  <div class="row">
    <div class="col-md-4">
      <button
        type="button"
        class="btn btn-block bg-gradient-primary mb-3"
        data-bs-toggle="modal"
        data-bs-target="#modal-default"
      >
        Default
      </button>
      <div
        class="modal fade"
        id="modal-default"
        tabindex="-1"
        role="dialog"
        aria-labelledby="modal-default"
        aria-hidden="true"
      >
        <div
          class="modal-dialog modal- modal-dialog-centered modal-"
          role="document"
        >
          <div class="modal-content">
            <div class="modal-header">
              <h6 class="modal-title" id="modal-title-default">
                Type your modal title
              </h6>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              >
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body">
              <p>
                Far far away, behind the word mountains, far from the countries
                Vokalia and Consonantia, there live the blind texts. Separated
                they live in Bookmarksgrove right at the coast of the Semantics,
                a large language ocean.
              </p>
              <p>
                A small river named Duden flows by their place and supplies it
                with the necessary regelialia. It is a paradisematic country, in
                which roasted parts of sentences fly into your mouth.
              </p>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn bg-gradient-primary">
                Save changes
              </button>
              <button
                type="button"
                class="btn btn-link ml-auto"
                data-bs-dismiss="modal"
              >
                Close
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-4">
      <button
        type="button"
        class="btn btn-block bg-gradient-warning mb-3"
        data-bs-toggle="modal"
        data-bs-target="#modal-notification"
      >
        Notification
      </button>
      <div
        class="modal fade"
        id="modal-notification"
        tabindex="-1"
        role="dialog"
        aria-labelledby="modal-notification"
        aria-hidden="true"
      >
        <div
          class="modal-dialog modal-danger modal-dialog-centered modal-"
          role="document"
        >
          <div class="modal-content">
            <div class="modal-header">
              <h6 class="modal-title" id="modal-title-notification">
                Your attention is required
              </h6>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              >
                <span aria-hidden="true">×</span>
              </button>
            </div>
            <div class="modal-body">
              <div class="py-3 text-center">
                <i class="ni ni-bell-55 ni-3x"></i>
                <h4 class="text-gradient text-danger mt-4">
                  You should read this!
                </h4>
                <p>
                  A small river named Duden flows by their place and supplies it
                  with the necessary regelialia.
                </p>
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-white">Ok, Got it</button>
              <button
                type="button"
                class="btn btn-link text-white ml-auto"
                data-bs-dismiss="modal"
              >
                Close
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-4">
      <button
        type="button"
        class="btn btn-block btn-default mb-3"
        data-bs-toggle="modal"
        data-bs-target="#modal-form"
      >
        Form
      </button>
      <div
        class="modal fade"
        id="modal-form"
        tabindex="-1"
        role="dialog"
        aria-labelledby="modal-form"
        aria-hidden="true"
      >
        <div
          class="modal-dialog modal-dialog-centered modal-sm"
          role="document"
        >
          <div class="modal-content">
            <div class="modal-body p-0">
              <div class="card card-plain">
                <div class="card-header pb-0 text-left">
                  <h3 class="font-weight-bolder text-info text-gradient">
                    Welcome back
                  </h3>
                  <p class="mb-0">Enter your email and password to sign in</p>
                </div>
                <div class="card-body">
                  <form role="form text-left">
                    <label>Email</label>
                    <div class="input-group mb-3">
                      <input
                        type="email"
                        class="form-control"
                        placeholder="Email"
                        aria-label="Email"
                        aria-describedby="email-addon"
                      />
                    </div>
                    <label>Password</label>
                    <div class="input-group mb-3">
                      <input
                        type="email"
                        class="form-control"
                        placeholder="Password"
                        aria-label="Password"
                        aria-describedby="password-addon"
                      />
                    </div>
                    <div class="form-check form-switch">
                      <input
                        class="form-check-input"
                        type="checkbox"
                        id="rememberMe"
                        checked=""
                      />
                      <label class="form-check-label" for="rememberMe"
                        >Remember me</label
                      >
                    </div>
                    <div class="text-center">
                      <button
                        type="button"
                        class="
                          btn btn-round
                          bg-gradient-info
                          btn-lg
                          w-100
                          mt-4
                          mb-0
                        "
                      >
                        Sign in
                      </button>
                    </div>
                  </form>
                </div>
                <div class="card-footer text-center pt-0 px-lg-2 px-1">
                  <p class="mb-4 text-sm mx-auto">
                    Don't have an account?
                    <a
                      href="javascript:;"
                      class="text-info text-gradient font-weight-bold"
                      >Sign up</a
                    >
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { defineComponent } from '@vue/composition-api'

export default defineComponent({
    setup() {
        
    },
})
</script>

